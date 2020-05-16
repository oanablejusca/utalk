import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { Profile } from 'src/app/models/profile';
import { ProfilesService } from 'src/app/services/profilesservice/profiles.service';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppComponent } from 'src/app/app.component';
import { Friends } from 'src/app/models/friend'
import { FriendService } from 'src/app/services/friendservice/friend.service';
import { UsersService } from 'src/app/services/usersservice/users.service';
import { User } from 'src/app/models/user';


@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FriendsComponent implements OnInit {

  allFriends: Friends[] = [];
  currentFriends: Profile[] = [];
  photoUrl: string = "";
  thisProfile: Profile = null;
  allProfiles: Profile[] = [];
  selectedFile: File = null;
  currentFriendsIds: number[] = [];
  friendSearched: string = "";
  isFriendValid:boolean=true;


  constructor(private profilesService: ProfilesService,
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private friendService: FriendService,
    private usersService: UsersService) {
    if (window.localStorage.getItem("profile-id")) {
      this.photoUrl = AppComponent.imagesPath;
      profilesService.getProfileById(parseInt(window.localStorage.getItem("profile-id"))).subscribe(
        (profileResponse: Profile) => {
          this.thisProfile = profileResponse;
        },
        (err) => { this.router.navigate(['/login']); }
      );
    } else {
      this.router.navigate(['/login']);
    }

    friendService.getAllFriends().subscribe(
      (friendsResponse: Friends[]) => {
        this.allFriends = friendsResponse as Friends[];

        this.allFriends.forEach((friend) => {
          if (friend.user_id1 == parseInt(window.localStorage.getItem("profile-id"))) {
            this.currentFriendsIds.push(friend.user_id2);
          }
        });
        this.currentFriendsIds.forEach((friendId) => {
          this.profilesService.getProfileById(friendId).subscribe(
            (profileResponse: Profile) => {
              this.currentFriends.push(profileResponse);
            }
          );
        });
      },
      (err) => {
        console.log(err);
      }

    );

  }


  areAllFriendsLoaded(): boolean {
    if (this.currentFriends && this.currentFriendsIds && this.currentFriends.length > 0 && this.currentFriendsIds.length == this.currentFriends.length) {
      return true;
    }
    return false;
  }



  search() {
    document.getElementById("search-button").setAttribute("style", "display:none;");
    document.getElementById("first-content").setAttribute("style", "display:block;");
  }

  addFriend() {
    var friendFound: User = null;
    this.usersService.getUsers().subscribe(
      (usersResponse: User[]) => {
        usersResponse.forEach(
          (userIterated) => {
            if (userIterated.username == this.friendSearched) {
              friendFound = userIterated;
            }
          }
        );
        if (friendFound && !this.currentFriendsIds.includes(friendFound.profile_id)) {
          var friendship: Friends = new Friends();
          friendship.user_id1 = parseInt(window.localStorage.getItem("profile-id"));
          friendship.user_id2 = friendFound.profile_id;
          this.friendService.createFriendship(friendship).subscribe(
            (friend: Friends) => {
              this.router.navigate(['/home']);
            }
          );
        }else{
          this.friendSearched="";
          this.isFriendValid=false;
          setTimeout(function(){
            this.isFriendValid=true;
          }, 1000);
        }
      }
    );
  }

  searchFailed():boolean{
    return !(this.isFriendValid);
  }

  onEnter(event: KeyboardEvent) {
    if (event.key == "Enter") {
      this.addFriend();
    }
  }


  ngOnInit() {
  }



}
