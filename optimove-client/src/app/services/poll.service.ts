import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Food as Item } from '../app.component';


@Injectable({
  providedIn: 'root'
})
export class PollService {

  items: Item[];
  url: string;

  isChartCompVisible : boolean = false;
  isBackVisible : boolean = false;
  serverUrls: string[] = [
    "127.0.0.1:8081",
    "127.0.0.1:8082"];
  

  constructor(private http: HttpClient) { 
    this.url = this.serverUrls[Math.floor(Math.random() * this.serverUrls.length) + 0];
    console.log("connected to: " + this.url)
    this.getItems("data");
  }


  getItems(type: string) {
    this.getItemsFromDB(type).subscribe(data => {
      this.items = data.itemsList;  
    });
  }

  toggleChartVisability(){
    this.isChartCompVisible = !this.isChartCompVisible;
  }
  toggleBackButtonVisabiliy(){
    this.isBackVisible = !this.isBackVisible;
  }

  //this.serversPortArray[Math.floor(Math.random() * this.serversPortArray.length - 1) + 0]

  update(id: string) {
    return this.http.put('http://' + this.url + '/update/' + id,null)
  }

  getItemsFromDB(type: string) : Observable<any> {
    return this.http.get<Item>('http://' + this.url + '/read?type=' + type);
  }

  // getRandomIp(): number{
  //   // let random : number  = 
  //   //   console.log(random);
  //   //   return random;
  // }
}
