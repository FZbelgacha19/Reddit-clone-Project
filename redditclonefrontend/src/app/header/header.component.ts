import { Component, OnInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private translateService : TranslateService) { }

  ngOnInit(): void {
  }

  public selectLanguage(event: any) {
      this.translateService.use(event.target.value);
  }
}
