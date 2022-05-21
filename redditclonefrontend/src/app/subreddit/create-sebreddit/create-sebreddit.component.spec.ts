import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateSebredditComponent } from './create-sebreddit.component';

describe('CreateSebredditComponent', () => {
  let component: CreateSebredditComponent;
  let fixture: ComponentFixture<CreateSebredditComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateSebredditComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateSebredditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
