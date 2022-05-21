import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {SubredditModel} from "../subreddit/subreddit-model";
import {Router} from "@angular/router";
import {SubredditService} from "../shared/subreddit.service";

@Component({
  selector: 'app-create-sebreddit',
  templateUrl: './create-sebreddit.component.html',
  styleUrls: ['./create-sebreddit.component.css']
})
export class CreateSebredditComponent implements OnInit {
  createSubredditForm: FormGroup;
  subredditModel: SubredditModel;
  title = new FormControl('');
  description = new FormControl('');

  constructor(private router: Router, private subredditService: SubredditService) {
    this.createSubredditForm = new FormGroup({
      title: new FormControl('', Validators.required),
      description: new FormControl('', Validators.required)
    });
    this.subredditModel = {
      name: '',
      description: ''
    }
  }

  ngOnInit() {
  }

  discard() {
    this.router.navigateByUrl('/').then(r => {
      console.log(r);
    });
  }

  createSubreddit() {
    this.subredditModel = {
      name: this.createSubredditForm.value.title,
      description: this.createSubredditForm.value.description
    }
    this.subredditService.createSubreddit(this.subredditModel).subscribe(data => {
      this.router.navigateByUrl('/list-subreddits');
    }, error => {
      console.log('Error occurred');
    })
  }
}
