import { Component, OnInit } from '@angular/core';
import { Profile } from 'src/app/models/profile';
import { ProfilesService } from 'src/app/services/profilesservice/profiles.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppComponent } from 'src/app/app.component';
import { UsersService } from 'src/app/services/usersservice/users.service';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-profiles',
  templateUrl: './profiles.component.html',
  styleUrls: ['./profiles.component.css']
})
export class ProfilesComponent implements OnInit {

  profile: Profile = null;
  allProfiles: Profile[] = [];
  currentPassword: string = "";
  selectedFile: File = null;


  constructor(private usersService: UsersService,
    private profilesService: ProfilesService,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient) {
    if (window.localStorage.getItem("profile-id")) {
      profilesService.getProfileById(parseInt(window.localStorage.getItem("profile-id"))).subscribe(
        (profileResponse: Profile) => {
          this.profile = profileResponse;
          if (this.profile.photo != "no-photo") {
            document.getElementById("profile-pic").setAttribute("src", AppComponent.imagesPath + this.profile.photo);
          } else {
            console.log("This profile has no photo yet");
          }
        },
        (err) => { this.router.navigate(['/login']); }
      );
    } else {
      this.router.navigate(['/login']);
    }

  }

  public onUpload() {
    const fd = new FormData();
    fd.append('file', this.selectedFile);
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'multipart/form-data');
    headers.append('Accept', 'application/json');
    this.http.post(AppComponent.serverUrl + "files", fd,
      { headers: headers }
    )
      .subscribe(() => {
        this.profile.photo = this.selectedFile.name;
        this.updateProfile(false);
      }
      );

  }

  public onFileSelected(event) {
    this.selectedFile = event.target.files[0];
    this.onUpload();
  }

  isPasswordValid(): boolean {
    if (this.currentPassword.length < 4 || this.currentPassword.includes(" ")) {
      return false;
    }
    return true;
  }

  public updateProfile(redirect: boolean) {
    this.profilesService.updateProfile(this.profile).subscribe(() => {
      if (this.isPasswordValid()) {
        this.usersService.getUserById(this.profile.id).subscribe(
          (userResponse: User) => {
            var currUser = userResponse;
            currUser.password = this.currentPassword;
            this.usersService.updateUser(currUser).subscribe(
              (user: User) => {
                if (redirect) {
                  this.router.navigate(['/home']);
                }
              },
              (err) => { console.log(err); }
            );
          }
        );
      }else{
        if (redirect) {
          this.router.navigate(['/home']);
        }
      }
    });
  }

  testIt() {
    console.log("Current password=" + this.currentPassword);
  }

  ngOnInit() {

  }

}


