// Copyright 2022 PingCAP, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.pingcap.recreateerror;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * LtoForecastSumDayanagerImpl
 *
 * @author Icemap
 * @date 2024/4/18
 */
@Repository
public class LtoForecastSumDayManagerImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static String sql = """
        select lfsd.product_code,lfsd.bp_code,sum(lfsd.pred_us) ai_us,max(lpp.esp_us) esp_us  from lto_forecast_sum_day lfsd
        join (
                SELECT bp_code,product_code,sum(pred_us) esp_us from lto_product_pred where 1= 1
                and market_code in ('1009')
                and bp_code in ('87710001')
                and product_code in ('2019120134-1')
                and day_date>='2024-01-15 00:00:00'
                and day_date<='2024-02-04 00:00:00'
        GROUP BY bp_code,product_code) as lpp
        on lpp.bp_code = lfsd.bp_code and
        lpp.product_code =  lfsd.product_code
        where 1 = 1
                and lfsd.market_code in ('1009')
                and lfsd.bp_code in ('87710001')
                and lfsd.product_code in ('2019120134-1')
                and lfsd.day_date>='2024-01-15 00:00:00'
                and lfsd.day_date<='2024-02-04 00:00:00'
          and lfsd.forecast_type = 'AI'
        group by lfsd.product_code,lfsd.bp_code
        """;

    public String justExecuteIt() {
        String result = "success";

        try {
            jdbcTemplate.execute(sql);
        } catch (Exception e) {
            // if it has any error here
            result = "ERROR: " + e.getMessage();
        }

        return result;
    }

    public String prepareAndExecuteIt() {
        String result = "success";

        try {
            jdbcTemplate.query(
                    con -> con.prepareStatement(sql),
                    rs -> null);
        } catch (Exception e) {
            // if it has any error here
            result = "ERROR: " + e.getMessage();
        }

        return result;
    }

    public String version() {
        String version = "";

        try {
            version = jdbcTemplate.query(
                    "SELECT VERSION() AS VERSION",
                    rs -> {
                        String v = "";
                        while (rs.next()) {
                            v = rs.getString("VERSION");
                        }
                        return v;
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }
}
