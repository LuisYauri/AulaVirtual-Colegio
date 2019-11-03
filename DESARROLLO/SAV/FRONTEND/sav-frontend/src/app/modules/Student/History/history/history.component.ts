import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss'],
})
export class HistoryComponent implements OnInit {
  tabs = ['Tareas', 'Examenes'];
  date = new Date();
  dateFormat = 'dd/MM/yyyy';
  constructor() {}

  ngOnInit() {}
}
