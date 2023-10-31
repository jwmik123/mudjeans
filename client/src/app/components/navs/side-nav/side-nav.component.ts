import { Component, OnInit, ElementRef } from '@angular/core';
declare const M: any;

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.scss']
})
export class SideNavComponent implements OnInit {

  constructor(private elRef: ElementRef) { }

  ngOnInit(): void {
    // const instance = new M.Sidenav(this.elRef.nativeElement.querySelector('#slide-out'));
    // instance.open();
  }

}
