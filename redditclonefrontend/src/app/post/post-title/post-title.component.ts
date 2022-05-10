import {Component, Input, OnInit} from '@angular/core';
import {PostModel} from "../post/post-Model";
import { faComments, faArrowUp,faArrowDown } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-post-title',
  templateUrl: './post-title.component.html',
  styleUrls: ['./post-title.component.css']
})
export class PostTitleComponent implements OnInit {

  @Input() data!: Array<PostModel>; // @Input annotation est pour dire que cette variable sera accessible dans le composant parent
  faComments = faComments;
  faArrowUp= faArrowUp;
  faArrowDown= faArrowDown;
  upvoteColor: any;
  downvoteColor: any;

  constructor() { }

  ngOnInit(): void {
  }

  upvotePost() {

  }

  downvotePost() {

  }

  goToPost(id: number) {
    
  }
}
