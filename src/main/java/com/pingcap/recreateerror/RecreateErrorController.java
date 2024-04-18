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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RecreateErrorController
 *
 * @author Icemap
 * @date 2024/4/18
 */
@RequestMapping("/")
@RestController
public class RecreateErrorController {
    @Autowired
    LtoForecastSumDayManagerImpl ltoImpl;

    @GetMapping("/execute")
    public String justExecuteIt() {
        return ltoImpl.justExecuteIt();
    }

    @GetMapping("/prepare")
    public String prepareAndExecuteIt() {
        return ltoImpl.prepareAndExecuteIt();
    }

    @GetMapping("/version")
    public String versionOfTiDB() {
        return ltoImpl.version();
    }
}
