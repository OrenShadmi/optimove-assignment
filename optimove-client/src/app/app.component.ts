import { Component } from '@angular/core';
import { PollService } from './services/poll.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'optimove-client';

  constructor(private pollService : PollService){}


  onBack(){
    this.pollService.isChartCompVisible = false;
    this.pollService.isBackVisible = false;
  }
}



export interface Food {
  id: string,
  name: string,
  votes: number,
  percentageFromTotal : number

}
