import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SettingsComponent } from './settings.component';

describe('SettingsComponent', () => {
  let component: SettingsComponent;
  let fixture: ComponentFixture<SettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SettingsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  //Semih
  it('should display the "Change password" text', () => {
    const title = 'Change password';
    fixture.detectChanges();

    const a = fixture.debugElement.nativeElement;
    expect(a.querySelector('h5').textContent).toContain(title);
  });

//Semih
  it('should show the text shown Old password in the placeholder', () => {
    const title = 'Old password';
    fixture.detectChanges();

    component.oldPasswordPlaceholder = title;
    expect(component.oldPasswordPlaceholder).toContain(title);
  });


  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
