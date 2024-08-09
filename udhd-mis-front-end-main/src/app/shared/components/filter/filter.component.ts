import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from "@angular/core";
import { PaginationModel } from "../../model/pagination-request.model";
import { SortOptionModel } from "../../model/sort-option.model";

@Component({
    selector: 'app-filter',
    templateUrl: './filter.component.html',
    styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnChanges {

    @Input() sortOptions!: SortOptionModel[];
    @Output() sortDirectionChange = new EventEmitter<PaginationModel>();
    @Output() clearDataEvent = new EventEmitter<PaginationModel>();
    @Output() filteredData = new EventEmitter<PaginationModel>();
    @Output() getSortedByEvent = new EventEmitter<PaginationModel>();
    sortingDirection = true;
    currentlySelectedVal = '';
    filterdata = '';


    onFilterData(data: string) {
        this.filterdata = data;
        const page = this.getPageModel(
            'ALL',
            0,
            10,
            this.currentlySelectedVal,
            this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
            this.filterdata
        );
        this.filteredData.emit(page);
    }
    clearData() {
        this.filterdata='';
        const page = this.getPageModel(
            'ALL',
            0,
            10,
            this.currentlySelectedVal,
            this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
            this.filterdata
        );
        this.clearDataEvent.emit(page);
    }
    ngOnChanges(changes: SimpleChanges) {
    }

    getSortedBy(val: string) {
        this.currentlySelectedVal = val;

        const page = this.getPageModel(
            'ALL',
            0,
            10,
            this.currentlySelectedVal,
            this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
            this.filterdata
        );
        this.getSortedByEvent.emit(page);
    }

    onToggleSortDirection(event: any) {
        this.sortingDirection = !this.sortingDirection;
        event.stopPropagation();
        const page = this.getPageModel(
            'ALL',
            0,
            10,
            this.currentlySelectedVal,
            this.sortingDirection ? 'ASCENDING' : 'DESCENDING',
            this.filterdata
        );
        this.sortDirectionChange.emit(page);
    }

    getPageModel(
        criteria: string,
        pageNo: number,
        pageSize: number,
        sortedBy: string,
        directionOfSort: string,
        queryString: string
    ): PaginationModel {
        const page = new PaginationModel();
        page.criteria = criteria;
        page.pageNo = pageNo;
        page.pageSize = pageSize;
        page.sortedBy = sortedBy;
        page.directionOfSort = directionOfSort;
        if (queryString != '') {
            page.queryString = queryString;
        }
        return page;
    }
}
