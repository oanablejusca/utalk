import { Component, OnInit } from '@angular/core';
import { Profile } from 'src/app/models/profile';
import { ProfilesService } from 'src/app/services/profilesservice/profiles.service';
import { AppComponent } from 'src/app/app.component';
import { UsersService } from 'src/app/services/usersservice/users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  profile: Profile = null;
  areLinksVisible: boolean = false;

  constructor(private router: Router,
    private profilesService: ProfilesService,
    private usersService: UsersService) {
  }

  ngOnInit() {
    if (window.localStorage.getItem("profile-id")) {
      this.profilesService.getProfileById(parseInt(window.localStorage.getItem("profile-id"))).subscribe(
        (profileResponse: Profile) => {
          this.profile = profileResponse;
          document.getElementById("profile-pic").setAttribute("src", AppComponent.imagesPath + this.profile.photo);
        },
        (err) => { console.log(err) }
      );
    } else {
      this.router.navigate(['/login']);
    }
    document.getElementById("useful-link").addEventListener("click", this.clickUsefulLinks, false);
  }

  clickUsefulLinks() {
    if (this.areLinksVisible) {
      document.getElementById("useful-link").style.color = "#aaa";
      document.getElementById("submenu").setAttribute("style", "visibility: hidden;");
      this.areLinksVisible = false;

    } else {
      document.getElementById("useful-link").style.color = "#333";
      document.getElementById("submenu").setAttribute("style", "visibility: visible;");
      this.areLinksVisible = true;
    }
  }

  logOut() {
    window.localStorage.removeItem("profile-id");
    this.router.navigate(['/login']);
  }
}
