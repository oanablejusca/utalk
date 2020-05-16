import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, observable } from 'rxjs';
import { AppComponent } from '../../app.component';
import { Profile } from 'src/app/models/profile';
import {Friends } from 'src/app/models/friend';


@Injectable({
  providedIn: 'root'
})
export class FriendService {

  constructor(private http: HttpClient) {
  }
  

  public getAllFriends(): Observable<Object>{
    return this.http.get(AppComponent.serverUrl + "friendship");
  }
	public getFriendshipById(id: number): Observable<Object> {
		return this.http.get(AppComponent.serverUrl + "friendship/" + id);
  }
  
	public createFriendship(friendship: Friends): Observable<Object> {
		return this.http.post(AppComponent.serverUrl + "friendship", friendship);
	}

	public deleteFriend(id: number): Observable<Object> {
		return this.http.delete(AppComponent.serverUrl + "friendship/" + id);
	}

}
