import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/app/services/usersservice/users.service';
import { User } from 'src/app/models/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  allUsers: User[] = [];
  constructor(private router: Router,
    private usersService: UsersService) {
  }


  ngOnInit() {
    this.user.username = "";
    this.user.password = "";
  }
  isLoginCorrect: boolean = true;
  isGoingToRegister: boolean = false;
  isFormValid: boolean = false;
  onChange() {
    if (this.user.password.length >= 4 && this.user.username.length >= 4 && !this.user.password.includes(" ") && !this.user.username.includes(" "))
      this.isFormValid = true;
    else
      this.isFormValid = false;
  }
  isValid() {
    return this.isFormValid;
  }

  onEnter(event: KeyboardEvent) {
    if (event.key == "Enter") {
      if (this.isValid())
        this.login();
    }
  }

  loginFailed():boolean{
    return !(this.isLoginCorrect);
  }

  login() {
    if (!this.isGoingToRegister) {
      this.usersService.getUsers().subscribe(
        (usersResponse: User[]) => {
          this.allUsers = usersResponse as User[];
          var currentUser: User = null;
          this.allUsers.forEach(userIterated => {
            if (userIterated.username == this.user.username) {
              currentUser = userIterated;
            }
          });
          if (currentUser && currentUser.password == this.user.password) {
            window.localStorage.setItem("profile-id", currentUser.profile_id + "");
            this.router.navigate(['/home']);
          } else {
            this.isLoginCorrect=false;
            this.user.username="";
            this.user.password="";
            setTimeout(function(){
              this.isLoginCorrect=true;
            }, 1000);
          }
        }

      );
    } else {
      this.usersService.createUser(this.user);
    }
  }

  wantToRegister() {
    this.isGoingToRegister = true;
    document.getElementById("login-button").innerHTML = "REGISTER";
  }

}
