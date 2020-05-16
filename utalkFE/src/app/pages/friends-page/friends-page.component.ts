import { Component, OnInit } from '@angular/core';
import { ProfilesService } from 'src/app/services/profilesservice/profiles.service';
import { Profile } from 'src/app/models/profile';
import { AppComponent } from 'src/app/app.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-friends-page',
  templateUrl: './friends-page.component.html',
  styleUrls: ['./friends-page.component.css']
})
export class FriendsPageComponent implements OnInit {
  currentProfile: Profile = null;
  constructor(private profilesService: ProfilesService,
    private router: Router) {

    if (window.localStorage.getItem("profile-id")) {
      profilesService.getProfileById(parseInt(window.localStorage.getItem("profile-id"))).subscribe(
        (profileResponse: Profile) => {
          this.currentProfile = profileResponse;
        },
        (err) => { this.router.navigate(['/login']); }
      );
    } else {
      this.router.navigate(['/login']);
    }
  }

  ngOnInit() {
  }

}
