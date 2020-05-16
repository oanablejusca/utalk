import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/models/post';
import { Profile } from 'src/app/models/profile';
import { ProfilesService } from 'src/app/services/profilesservice/profiles.service';
import { PostsService } from 'src/app/services/postsservice/posts.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Friends } from 'src/app/models/friend';
import { AppComponent } from 'src/app/app.component';
import { FriendService } from 'src/app/services/friendservice/friend.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  //From posts
  currentPosts: Post[] = [];
  allPosts: Post[] = [];
  currentProfile: Profile=null;

  allFriends: Friends[] = [];
  currentFriends: Profile[] = [];
  photoUrl: string = "";
  thisProfile: Profile = null;
  allProfiles: Profile[] = [];
  selectedFile: File = null;
  currentFriendsIds: number[] = [];


  constructor(private postsService: PostsService,
    private profilesService: ProfilesService,
    private route: ActivatedRoute,
    private router: Router,
    private friendService: FriendService) {

      if(!window.localStorage.getItem("profile-id")){
        this.router.navigate(['/login']);
      }
      profilesService.getProfiles().subscribe(
        (profilesResponse: Profile[]) => {
          var allProfiles = profilesResponse as Profile[];
          allProfiles.forEach((profile)=>{
            if(profile.id==parseInt(window.localStorage.getItem("profile-id"))){
              this.currentProfile = profile;
            }
          });

          postsService.getPosts().subscribe(
            (postsResponse: Post[]) => {
              this.allPosts = postsResponse as Post[];
              this.allPosts.forEach(element => {
                if(element.profile_id==this.currentProfile.id){
                  this.currentPosts.push(element);
                }
              });
              this.currentPosts.sort(
                (postA, postB)=>{
                  return (new Date(postA.posted_on).getTime()>new Date(postB.posted_on).getTime()?-1:1);
                }
              );
            }
            , (err) => {
              console.log(err);
            }
          );

        }
        , (err) => {
          console.log(err);
        }
      );
  
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

  ngOnInit() {
  }

}
