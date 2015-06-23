create view final_results as
select c.id as c_id, c.name as c_name, i.id as i_id, i.name as i_name, l.id as l_id , l.name as l_name, l.type, p.id as p_id, p.name as p_name, p.is_male, l.distance, r.time
from results r
inner join check_points cp on r.check_point_id = cp.id
inner join legs l on cp.leg_id = l.id
inner join instances i on l.instance_id = i.id
inner join competitions c on i.competition_id = c.id
inner join participants p on r.participant_id = p.id
where cp.is_last = true
and r.time > 0