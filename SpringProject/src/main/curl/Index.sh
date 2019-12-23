curl -X PUT "http://localhost:9200/conferences" -H "Content-Type: application/json" --data-binary @index.json
{
  "query": {
    "bool": {
      "must": {
        "match_all": {}
      },
      "filter": {
        "term": {
          "type": ""
        }
      }
    }
  }
}