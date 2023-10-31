import { Component, ElementRef, OnInit } from '@angular/core';

declare const M: any;

@Component({
  selector: 'app-top-nav',
  templateUrl: './top-nav.component.html',
  styleUrls: ['./top-nav.component.scss']
})
export class TopNavComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    const menuDropdownElem = document.querySelector('#profile-dropdown-trigger');
    const dropdownInstances = M.Dropdown.init(menuDropdownElem);

    const mobileMenuElems = document.querySelectorAll('.sidenav');
    const mobileMenuInstances = M.Sidenav.init(mobileMenuElems);
  }

}
