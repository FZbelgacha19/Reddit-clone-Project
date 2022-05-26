import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VoteBottonComponent } from './vote-botton.component';

describe('VoteBottonComponent', () => {
  let component: VoteBottonComponent;
  let fixture: ComponentFixture<VoteBottonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ VoteBottonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(VoteBottonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
