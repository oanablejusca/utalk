import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';
import { UsersService } from 'src/app/services/usersservice/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {
  jspUrl: string ="";
  constructor(private usersService: UsersService,
    private router: Router) {
    if (!window.localStorage.getItem("profile-id")) {
      this.router.navigate(['/login']);
    }
    this.jspUrl = AppComponent.jspUrl;
   }

  ngOnInit() {
    var contactLink=document.getElementById("contact-link");
    contactLink.style.color="#333";
    contactLink.style.cursor="default";
    contactLink.style.userSelect="none";
    document.getElementById("contact-content").setAttribute("src", this.jspUrl+"contact.jsp");
  }

}
