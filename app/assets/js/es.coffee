$ ->
  renderGroupStats = (g) ->
    "<tr><td>#{g.name}</td><td>#{g.fetch.formattedTime}</td>
     <td>#{g.query.formattedTime}</td><td>#{g.query.count}</td>
     <td>#{(g.query.time / g.query.count).toFixed(0)}</td></tr>"

  indexToObj = (name, index) ->
    name: name
    groups: (groupToObj(name, group) for name, group of index.total.search.groups)

  groupToObj = (grpName, grp) ->
    name: grpName
    query:
      count: grp.query_total
      time: grp.query_time_in_millis
      formattedTime: grp.query_time
    fetch:
      count: grp.fetch_total
      time: grp.fetch_time_in_millis
      formattedTime: grp.fetch_time

  tableTemplate = (tableBody) ->
    "<table class='table table-striped'>
      <thead>
        <tr>
          <th>Stats Group</th>
          <th>Fetch Time</th>
          <th>Query Total Time</th>
          <th>Query Requests</th>
          <th>Query Avg Req Time</th>
        </tr>
      </thead>
    <tbody>
      #{tableBody}
    </tbody>
    </table>"

  refresh = ->
    $.getJSON "http://#{hostname}:9200/_all/_stats?groups=_all", (data) ->

      indices = (indexToObj(name, index) for name, index of data.indices)

      groups = (groupToObj(name, group) for name, group of data._all.total.search.groups)

      groups.sort (a,b) ->
        b.query.time - a.query.time

      tableBody = (renderGroupStats(g) for g in groups)

      html = "#{renderGroupStats(groupToObj("(overall)", data._all.total.search))} #{tableBody.join(" ")}"

      indexHtml = ("<h4>#{index.name}</h4>#{tableTemplate(
        (renderGroupStats(g) for g in index.groups.sort (a,b) -> b.query.time - a.query.time).join(" ")
      )}" for index in indices).join(" ")

      $("#stats-body").html(html)
      $("#indices").html(indexHtml)

  refresh()

  setInterval refresh, 30000
