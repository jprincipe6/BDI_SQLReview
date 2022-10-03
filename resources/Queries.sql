
-- Select the number of conference belonging to each ranking.
SELECT count(*) AS numberOfConferences, ranking
    FROM conference
    GROUP BY ranking
    ORDER BY ranking ASC;

-- Select all the abstracts of the papers written (or co-written)
-- by the author whose name is ‘Alex’ or ‘Alexander’.

SELECT paper.title, paper.abstract, author.name
    FROM paper INNER JOIN
            writes ON writes.paperid = paper.paperid INNER JOIN
                author ON author.authorid = writes.authorid
    WHERE author.name LIKE ('Ga%')
        OR author.name LIKE ('Gr%');

-- Select the names of all the authors that cited themselves.
SELECT author.name, cites.paperIdFrom, cites.paperIdto
    FROM author
        INNER JOIN writes
            ON  writes.authorId = author.authorId
        INNER JOIN cites
            ON cites.paperIdto = writes.paperId
    WHERE
        author.authorId IN (SELECT writes.authorId
            FROM writes
            WHERE cites.paperIdFrom = writes.paperId);

-- Create a view PublishesIn(authorID, confID) containing all the pairs (authorID, confID)
-- such that there is at least one publication by authorID accepted at confID.
CREATE VIEW PublishesIn AS
  SELECT writes.authorId, conference.confId
  FROM writes
      INNER JOIN submits
        ON submits.paperId = writes.paperid
      INNER JOIN conference
        ON conference.confId = submits.confId
  WHERE submits.isAccepted = TRUE
  GROUP BY (writes.authorId,conference.confId)
  ORDER BY writes.authorid desc;


-- Select the title of all the papers co-authored by the author ‘Alex’ (the papers
-- where ‘Alex’ is the only author should not be part of the result).
SELECT paper.title
    FROM paper
        INNER JOIN writes
            ON writes.paperId = paper.paperId
        INNER JOIN author
            ON author.authorId = writes.authorId
    WHERE
        author.name Like ('A%')
        AND
        writes.paperId IN (
                SELECT writes.paperId
                   FROM paper
                        INNER JOIN writes
                           ON writes.paperId = paper.paperId
                       GROUP BY (writes.paperID)
                       HAVING COUNT(writes.paperId)> 1);