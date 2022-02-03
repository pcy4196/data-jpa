package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam();

    // 중첩구조부터 최적화 X
    interface TeamInfo {
        String getName();
    }

//    select
//    member0_.username as col_0_0_,
//    team1_.team_id as col_1_0_,
//    team1_.team_id as team_id1_2_,
//    team1_.created_date as created_2_2_,
//    team1_.last_modified_date as last_mod3_2_,
//    team1_.created_by as created_4_2_,
//    team1_.last_modified_by as last_mod5_2_,
//    team1_.name as name6_2_
//            from
//    member member0_
//    left outer join
//    team team1_
//    on member0_.team_id=team1_.team_id
//            where
//    member0_.username=?
}
