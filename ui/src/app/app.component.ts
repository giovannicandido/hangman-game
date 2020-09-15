import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { WordPlaceholderComponent } from './word-placeholder/word-placeholder.component';
import { GameResponse } from './model/game-response';
import { ToastrService } from 'ngx-toastr';
import { catchError } from 'rxjs/operators';
import { throwError } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  @ViewChild(WordPlaceholderComponent)
  wordPlaceComponent: WordPlaceholderComponent
  attemptsRemaining: number = 6;

  constructor(private http: HttpClient, private toast: ToastrService) {
  }

  ngOnInit(): void {
  }

  init() {
    this.http.get<number>('/api/new')
      .subscribe(r => this.wordPlaceComponent.init(r))
  }

  guess(letter: string) {
    this.http.post<GameResponse>('/api/guess', {letter: letter})
      .pipe(
        catchError(err => {
          this.toast.error(err.error.message)
          return throwError(err)
        })
      )
      .subscribe((response) => {
        for (let position of response.positions) {
          this.wordPlaceComponent.markPosition(position, response.letter);
        }
        this.attemptsRemaining = response.attemptsRemaining;
        this.checkIfWonOrLost(response);
      })
  }

  checkIfWonOrLost(response: GameResponse) {
    console.log(response)
    if(response.gameWon) {
      this.toast.success('Congratulations you won the game');
    }else if(response.gameLost) {
      this.toast.error('You lost, try a new word', '', {

      });
    }
  }

}
