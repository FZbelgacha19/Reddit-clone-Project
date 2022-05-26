export class CreatePostPayload {
  postName!: string;
  subredditName?: string; // ? mean the value is optional
  url?: string;
  description!: string;
}
