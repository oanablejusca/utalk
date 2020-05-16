import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, observable } from 'rxjs';
import { AppComponent } from '../../app.component';
import { Post } from 'src/app/models/post';

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  constructor(private http: HttpClient) { }

  public getPosts(): Observable<Object> {
		return this.http.get(AppComponent.serverUrl + "posts");
	}

	public getPostingById(profile_id: number): Observable<Object> {
		return this.http.get(AppComponent.serverUrl + "posts/" + profile_id);
	}

	public createPosting(posting: Post): Observable<Object> {
		return this.http.post(AppComponent.serverUrl + "posts",posting);
	}

	public updatePosting(posting: Post): Observable<Object> {
		let profile_id: number = posting === null ? null : posting.profile_id;
		return this.http.put(AppComponent.serverUrl + "posts/" + profile_id, posting);
	}
	
	public deletePosting(id: number): Observable<Object> {
		return this.http.delete(AppComponent.serverUrl + "posts/" + id);
	}
}
