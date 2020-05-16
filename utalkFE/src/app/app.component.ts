import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'utalkFE';
  static serverUrl = "http://localhost:8081/";
  static clientUrl = "http://localhost:4200/";
  static jspUrl = "http://localhost:8080/JSP/";
  static imagesPath = "../../../assets/";
  constructor(private router: Router) {
  }
}