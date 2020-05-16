import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-newsfeed',
  templateUrl: './newsfeed.component.html',
  styleUrls: ['./newsfeed.component.css']
})
export class NewsfeedComponent implements OnInit {

  constructor(private router:Router) { 
    if(window.localStorage.getItem("profile-id")){

    }else{
        this.router.navigate(['\login']);
    }
  }

  ngOnInit() {
  }

}
