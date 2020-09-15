import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-word-placeholder',
  templateUrl: './word-placeholder.component.html',
  styleUrls: ['./word-placeholder.component.css']
})
export class WordPlaceholderComponent implements OnInit {
  letters = []

  constructor() { }

  ngOnInit(): void {
  }

  init(size: number) {
    this.letters = [];
   for(let i=0; i<size;i++) {
     this.letters[i] = '?'
   }
  }

  markPosition(position: number, letter: string) {
    this.letters[position] = letter;
  }

}
