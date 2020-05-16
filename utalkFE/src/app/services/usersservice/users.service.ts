import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppComponent } from 'src/app/app.component';
import { User } from 'src/app/models/user';
import { ProfilesService } from '../profilesservice/profiles.service';
import { Profile } from 'src/app/models/profile';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  allProfiles: Profile[] = [];
  profile: Profile = null;
  constructor(private http: HttpClient,
    private profilesService: ProfilesService,
    private usersService: UsersService,
    private router: Router) {
  }

  public getUsers(): Observable<Object> {
    return this.http.get(AppComponent.serverUrl + "users");
  }

  public getUserById(id: number): Observable<Object> {
    return this.http.get(AppComponent.serverUrl + "users/" + id);
  }

  public createUser(user: User) {
    var profile: Profile = new Profile();
    profile.location = "";
    profile.name = "";
    profile.occupation = "";
    profile.photo = "profile1.jpg";
    profile.birthdate = new Date();

    this.profilesService.createProfile(profile).subscribe(
      (profileResponse: Profile) => {
        user.profile_id = profileResponse.id;
        this.http.post(AppComponent.serverUrl + "users", user).subscribe(
          (userResponse: User) => {
            window.localStorage.setItem("profile-id", userResponse.profile_id + "");
            this.router.navigate(['\profiles']);
          }

        );
      }
      , (err) => {
        console.log(err);
      }
    );
  }

  public updateUser(user: User): Observable<Object> {
    return this.http.put(AppComponent.serverUrl + "users/" + user.profile_id, user);
  }

  public deleteUser(id: number): Observable<Object> {
    return this.http.delete(AppComponent.serverUrl + "users/" + id);
  }
}
