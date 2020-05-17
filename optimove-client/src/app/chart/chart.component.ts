import { Component, OnInit } from '@angular/core';
import * as CanvasJS from '../../assets/canvasjs.min';
import { PollService } from '../services/poll.service';
import { Food } from '../app.component';

@Component({
  selector: 'app-chart',
  templateUrl: './chart.component.html',
  styleUrls: ['./chart.component.css']
})
export class ChartComponent implements OnInit {

  dataPoints = [];


  constructor(private pollService: PollService) { }

  ngOnInit() {

    this.createDataPointsArrayFromData();

    let chart = new CanvasJS.Chart("chartContainer", {
      theme: "light2",
      axisY: {
        includeZero: false,
      },
      animationEnabled: true,
      exportEnabled: true,

      data: [{
        type: "pie",
        showInLegend: true,
        toolTipContent: "<b>{name}</b>: #percent%",
        indexLabel: "{name} - #percent%",
         dataPoints: this.dataPoints
      }]
    });

    chart.render();
  }
  createDataPointsArrayFromData() {
    for(let i = 0 ; i < this.pollService.items.length ; i++){
      this.dataPoints.push({y :this.pollService.items[i].percentageFromTotal, name: this.pollService.items[i].name })
    }
    
  }
}


