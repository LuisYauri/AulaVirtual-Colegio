import {Component, OnInit, Input} from '@angular/core';
import {NzModalRef} from 'ng-zorro-antd/modal';
import {ConfirmationAnswer, Question, Questions} from "../../model/homework-student.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NzMessageService, NzNotificationService} from "ng-zorro-antd";
import {HomeworkStudentService} from "../../services/homework-student.service";
import {AuthService} from "../../../../../services/auth.service";

@Component({
  selector: 'app-modal-questions',
  templateUrl: './modal-questions.component.html',
  styleUrls: ['./modal-questions.component.scss']
})
export class ModalQuestionsComponent implements OnInit {
  @Input() questions: Questions

  indexTab = 0
  answersForm: FormGroup
  checkConfirm: ConfirmationAnswer = {confirmacion: false}

  constructor(private modal: NzModalRef, private fb: FormBuilder, private nzMessageService: NzMessageService,
              private homeworkStudentService: HomeworkStudentService, private authService: AuthService,
              private notification: NzNotificationService) {

  }

  ngOnInit() {
    this.getAnswersForm()
  }

  pre(): void {
    this.indexTab -= 1;
  }

  next(): void {
    this.indexTab += 1;
  }

  pressTab($event: any) {
    this.indexTab = $event
  }

  private getAnswersForm() {
    this.answersForm = this.fb.group(this.gAnwersFrom())
  }

  gAnwersFrom() {
    let tempObject = {}
    for (let i = 0; i < this.questions.lsPreguntas.length; i++) {
      tempObject[i] = [''];
    }
    return tempObject
  }

  sendAnswers() {
    if (!this.answersForm.valid) {
      for (const i in this.answersForm.controls) {
        this.answersForm.controls[i].markAsDirty()
        this.answersForm.controls[i].updateValueAndValidity()
      }
      return
    } else {
      this.confirmationSend()
    }
  }

  private async confirmationSend() {
    try {
      const response = await this.homeworkStudentService.postAnswers(this.questions.idTarea.toString(), this.gJsonSendAnswers(this.questions)).toPromise()
      console.log('Ingreso')
    } catch (e) {
      console.log(e)
    }
  }


  private gJsonSendAnswers(questions: Questions) {
    return {
      idTarea: questions.idTarea,
      titulo: questions.titulo,
      idEstudiante: this.authService.getIdEstudiante(),
      lsPreguntas: this.gJsonAnswers(questions.lsPreguntas)
    }
  }

  private gJsonAnswers(lsPreguntas: Question[]) {
    let tempArray = []
    for (let i = 0; i < lsPreguntas.length; i++) {
      tempArray.push({
        idPregunta: lsPreguntas[i].idPregunta,
        idRecurso: lsPreguntas[i].idRecurso,
        descripcion: lsPreguntas[i].descripcion,
        urlImagen: lsPreguntas[i].urlImagen,
        tipo: lsPreguntas[i].tipo,
        alternativas: lsPreguntas[i].alternativas,
        respuestaEstudiante: Number(this.answersForm.controls[i].value)
      })
    }
    return tempArray
  }

  async checkAnswer(question: Question, i: number) {
    try {
      const response: any = await this.homeworkStudentService.postCheckAnswer(question.idPregunta.toString(), this.gJsonCheckAnswer(i)).toPromise()
      this.checkConfirm = response
      if(this.checkConfirm.confirmacion){
        this.notification.create(
          'success',
          'Respuesta Correcta',
          'Sigue así.'
        );
      }else {
        this.notification.create(
          'error',
          'Respuesta Correcta',
          'Ánimos, tu puedes.'
        );
      }
    } catch (e) {
      this.notification.create(
        'error',
        'Respuesta Correcta',
        'Ánimos, tu puedes.'
      );
      console.log(e)
    }
  }

  gJsonCheckAnswer(i: number) {
    return {
      respuesta: Number(this.answersForm.controls[i].value)
    }
  }
}
