import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, observable } from 'rxjs';
import { AppComponent } from '../../app.component';
import { Profile } from 'src/app/models/profile';

@Injectable({
  providedIn: 'root'
})
export class ProfilesService {

  constructor(private http: HttpClient) {
	}

	public getProfiles(): Observable<Object> {
		return this.http.get(AppComponent.serverUrl + "profiles");
	}

	public getProfileById(id: number): Observable<Object> {
		return this.http.get(AppComponent.serverUrl + "profiles/" + id);
	}

	public createProfile(profile: Profile): Observable<Object> {
		return this.http.post(AppComponent.serverUrl + "profiles", profile);
	}

	public updateProfile(profile: Profile): Observable<Object> {
		let id: number = profile === null ? null : profile.id;
		return this.http.put(AppComponent.serverUrl + "profiles/" + id, profile);
	}
	
	public deleteProfile(id: number): Observable<Object> {
		return this.http.delete(AppComponent.serverUrl + "profiles/" + id);
	}
}
