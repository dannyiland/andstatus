/* 
 * Copyright (c) 2013 yvolk (Yuri Volkov), http://yurivolkov.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.andstatus.app.account;

import android.test.InstrumentationTestCase;

import org.andstatus.app.MyContextHolder;
import org.andstatus.app.TestSuite;
import org.andstatus.app.account.AccountName;
import org.andstatus.app.account.MyAccount;
import org.andstatus.app.origin.Origin;
import org.andstatus.app.origin.OriginType;
import org.andstatus.app.util.TriState;

public class MyAccountTest  extends InstrumentationTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        TestSuite.initialize(this);
    }

    public void testNewAccountCreation() {
       createAccountOfOriginType("", OriginType.TWITTER);
       createAccountOfOriginType("testUser1", OriginType.TWITTER);
       createAccountOfOriginType("", OriginType.PUMPIO);
       createAccountOfOriginType("test2User@somepipe.example.com", OriginType.PUMPIO);
       createAccountOfOriginType("PeterPom", OriginType.STATUSNET);
    }
    
    private void createAccountOfOriginType(String userName, OriginType originType) {
        Origin origin = MyContextHolder.get().persistentOrigins().firstOfType(originType);
        MyAccount.Builder builder = MyAccount.Builder.newOrExistingFromAccountName(userName + AccountName.ORIGIN_SEPARATOR + origin.getName(), TriState.UNKNOWN);
        assertEquals("Creating account for '" + originType + "'", origin.getId(), builder.getAccount().getOriginId());
        assertEquals("Creating account for '" + originType + "'", userName + AccountName.ORIGIN_SEPARATOR + origin.getName(), builder.getAccount().getAccountName());
    }
}
