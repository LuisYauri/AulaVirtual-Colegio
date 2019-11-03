import {Injectable} from '@angular/core';
import {environment} from "../../../../../environments/environment";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../../../../services/auth.service";
import {Answer, SendAnswers} from "../model/homework-student.model";

@Injectable({
  providedIn: 'root'
})
export class HomeworkStudentService {

  private API_URL_LIST_HOMEWORK = `${environment.apiMain}tareas?idEstudiante=`
  private API_URL_QUESTIONS = `${environment.apiMain}tareas/`
  private API_URL_SEND_ANSWERS = `${environment.apiMain}tareas/`
  private API_URL_CHECK_ANSWER = `${environment.apiMain}preguntas/`

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  private headersList() {
    return {
      headers: {
        Authorization: `Bearer ${this.authService.getToken()}`
      }
    };
  }

  getListHomework() {
    return this.http.get(`${this.API_URL_LIST_HOMEWORK}${this.authService.getIdEstudiante()}`, this.headersList())
  }

  getQuestions(idTarea: string) {
    return this.http.get(`${this.API_URL_QUESTIONS}${idTarea}/preguntas`, this.headersList())
  }

  postAnswers(idTarea: string, data: SendAnswers) {
    return this.http.post(`${this.API_URL_SEND_ANSWERS}${idTarea}/finalizar`, data, this.headersList())
  }

  postCheckAnswer(idPregunta: string, data: Answer) {
    return this.http.post(`${this.API_URL_CHECK_ANSWER}${idPregunta}/comprobar`, data, this.headersList())
  }
}
