import {Component, Input, OnInit} from '@angular/core';
import {PostModel} from "../post/post-Model";
import { faComments, faArrowUp,faArrowDown } from '@fortawesome/free-solid-svg-icons';
import {PostService} from "../shared/post.service";
import {Router} from "@angular/router";

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
  posts!: PostModel[];

  constructor(private postService: PostService, private router: Router) {
    this.postService.getAllPosts().subscribe(post => {
      this.posts = post;
    });
  }

  ngOnInit(): void {
    this.posts = this.data;
  }

  goToPost(id: number): void {
    this.router.navigateByUrl('/post/view-post/' + id);
  }


}
