select count(*) as numberConference, ranking from conference group by ranking order by ranking desc;

select paper.title, paper.abstract, author.name from paper inner join writes on writes.paperid = paper.paperid inner
	join author on author.authorid = writes.authorid where author.name in ('Moises','Karl');

  select author.name, cites.paperIdFrom, cites.paperIdto from author inner join writes on  writes.authorId = author.authorId inner join
	cites on cites.paperIdfrom = writes.paperId where cites.paperIdFrom = cites.paperIdto;

  CREATE VIEW PublishesIn AS select writes.authorId, conference.confId from writes inner join submits on submits.paperId = writes.paperid
  inner join conference on conference.confId = submits.confId
  	where submits.isAccepted = TRUE group by(writes.authorId,conference.confId)
  	order by writes.authorid desc;
