import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuditCommentDTO } from 'src/app/audit/ag-ir/models/agircomment.model';
import { AGIRAuditCommentService } from '../comments.service';

@Component({
  selector: 'app-view-agir-audit-comments',
  templateUrl: './view-agir-audit-comments.component.html',
  styleUrls: ['./view-agir-audit-comments.component.css']
})
export class ViewAgirAuditCommentsComponent implements OnInit {

  @Output() editCommentEvent = new EventEmitter<number>();
  @Output() listCommentEvent = new EventEmitter<string>();
  @Input() parent!: string;
  @Input() criteriaId!: number;
  @Input() commentId!: number;
  comment$!: Observable<AuditCommentDTO>;
  constructor(private agirAuditCommentsService: AGIRAuditCommentService) {
  }

  ngOnInit(): void {
    
   this.comment$ = this.agirAuditCommentsService.findAGIRAuditCommentById(this.commentId);
  }

  toList(){
    this.listCommentEvent.emit();
  }
  toEdit(comment:number){
    this.editCommentEvent.emit(comment);
  }
}
