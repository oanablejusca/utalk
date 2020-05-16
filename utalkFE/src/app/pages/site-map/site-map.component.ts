import { Component, OnInit } from '@angular/core';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-site-map',
  templateUrl: './site-map.component.html',
  styleUrls: ['./site-map.component.css']
})
export class SiteMapComponent implements OnInit {

  jspUrl: string ="";
  constructor() {
    this.jspUrl = AppComponent.jspUrl;
   }

  ngOnInit() {
    var sitemapLink=document.getElementById("sitemap-link");
    sitemapLink.style.color="#333";
    sitemapLink.style.cursor="default";
    sitemapLink.style.userSelect="none";
    
    document.getElementById("site-map-content").setAttribute("src", this.jspUrl+"sitemap.jsp");
  }

  showMoreInfo(){
    document.getElementById("more-info").style.display="none";
    document.getElementById("my-info").setAttribute("src", this.jspUrl+"SiteInfo");
    document.getElementById("my-info").style.display="initial";
  }

}
