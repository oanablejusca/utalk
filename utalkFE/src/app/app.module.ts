import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { ProfilesComponent } from './pages/profiles/profiles.component';
import { PostsComponent } from './pages/posts/posts.component';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { FriendsComponent } from './pages/friends/friends.component';
import { ContactComponent } from './pages/contact/contact.component';
import { SiteMapComponent } from './pages/site-map/site-map.component';
import { AboutUsComponent } from './pages/about-us/about-us.component';
import { LoginComponent } from './pages/login/login.component';
import { FriendsPageComponent } from './pages/friends-page/friends-page.component';
import { NewsfeedComponent } from './pages/newsfeed/newsfeed.component';
import { HomePageComponent } from './pages/home-page/home-page.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home', component: HomePageComponent },
  { path: 'posts', component: PostsComponent },
  { path: 'friends', component: FriendsPageComponent },
  { path: 'contact', component: ContactComponent },
  { path: 'profiles', component: ProfilesComponent },
  { path: 'sitemap', component: SiteMapComponent },
  { path: 'aboutus', component: AboutUsComponent },
  { path: 'newsfeed', component: NewsfeedComponent }
]


@NgModule({
  declarations: [
    AppComponent,
    ProfilesComponent,
    PostsComponent,
    HomeComponent,
    FriendsComponent,
    ContactComponent,
    SiteMapComponent,
    AboutUsComponent,
    LoginComponent,
    FriendsPageComponent,
    NewsfeedComponent,
    HomePageComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    FormsModule
  ],
  exports: [
    RouterModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
