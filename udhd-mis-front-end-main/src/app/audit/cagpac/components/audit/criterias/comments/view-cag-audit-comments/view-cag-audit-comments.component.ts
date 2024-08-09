import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuditCommentDTO } from 'src/app/audit/cagpac/models/cag-comment.model';
import { CAGAuditCommentService } from '../comments.service';

@Component({
  selector: 'app-view-cag-audit-comments',
  templateUrl: './view-cag-audit-comments.component.html',
  styleUrls: ['./view-cag-audit-comments.component.css']
})
export class ViewCagAuditCommentsComponent implements OnInit {

  @Output() editCommentEvent = new EventEmitter<number>();
  @Output() listCommentEvent = new EventEmitter<string>();
  @Input() parent!: string;
  @Input() criteriaId!: number;
  @Input() commentId!: number;
  comment$!: Observable<AuditCommentDTO>;
  constructor(private cagAuditCommentsService: CAGAuditCommentService) {
  }

  ngOnInit(): void {
    
   this.comment$ = this.cagAuditCommentsService.findCAGAuditCommentById(this.commentId);
  }

  toList(){
    this.listCommentEvent.emit();
  }
  toEdit(comment:number){
    this.editCommentEvent.emit(comment);
  }
}
