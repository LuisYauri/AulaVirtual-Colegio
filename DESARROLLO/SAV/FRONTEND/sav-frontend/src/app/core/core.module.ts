import { NgModule } from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SharedModule} from 'src/app/shared/shared.module';
import { MenuLeftStudentComponent } from './components/Student/MenuLeft/menu-left-student.component';
import { HeaderStudentComponent } from './components/Student/Header/header-student.component';
import { HeaderPageStudentComponent } from './components/Student/HeaderPage/header-page-student.component';
import { TitlePageStudentComponent } from './components/Student/TitlePage/title-page-student.component';
import { ModalHelpStudentComponent } from './components/Student/ModalHelp/modal-help-student.component';
import { SafeStudentPipe } from './pipe/Student/safe-student.pipe';

const COMPONENTS = [
  MenuLeftStudentComponent,
  HeaderStudentComponent,
  HeaderPageStudentComponent,
  TitlePageStudentComponent,
  ModalHelpStudentComponent];

@NgModule({
  declarations: [...COMPONENTS, SafeStudentPipe, ],
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
  exports: [...COMPONENTS],
  entryComponents: [ModalHelpStudentComponent]
})
export class CoreModule { }
