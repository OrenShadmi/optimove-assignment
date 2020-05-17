import { Component, OnInit } from '@angular/core';
import { PollService } from '../services/poll.service';
import { Food } from '../app.component';

@Component({
  selector: 'app-food-cont',
  templateUrl: './food-cont.component.html',
  styleUrls: ['./food-cont.component.css']
})
export class FoodContComponent implements OnInit {




  constructor(private pollService: PollService) { }

  ngOnInit() {
    // console.log(this.pollService.food);
  }

  onImageClick(id: string) {
    console.log(id);
    this.pollService.update(id).subscribe(() => {
        this.pollService.getItemsFromDB("stats").subscribe(data => {
          this.pollService.items = data.itemsList;
          console.log(data);
          this.pollService.isChartCompVisible = true;
          this.pollService.isBackVisible = true;
        })
    })
  }
}
