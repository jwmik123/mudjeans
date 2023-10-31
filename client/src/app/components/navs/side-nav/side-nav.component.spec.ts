import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SideNavComponent } from './side-nav.component';

describe('SideNavComponent', () => {
  let component: SideNavComponent;
  let fixture: ComponentFixture<SideNavComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SideNavComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SideNavComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // Michael Test 1
  it('should show title MUD JEANS', (() => {
    const nativeElement = fixture.debugElement.nativeElement;
    // Search for the element h3 and retrieve the text
    expect(nativeElement.querySelector('h3').textContent)
      // The text is being compared to the title
      .toContain('MUD JEANS');
  }));

  // Michael Test 2
  it('should load images correctly',  () => {
    const nativeElement = fixture.debugElement.nativeElement;
    // checks if the i element is there, if so the materialize icons is loaded
    expect(nativeElement.querySelector('i')).toBeTruthy();

  });
});
