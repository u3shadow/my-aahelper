package com.example.lxiao.aahelper.business;

import android.content.Context;

import com.example.lxiao.aahelper.R;
import com.example.lxiao.aahelper.model.PayOut;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lxiao on 2015/2/25.
 */
public class businessstatistic extends businessbase {
    private businesspayout mbusinesspayout;
    private businessuser mbusinessuser;
    private businessbook mbusinessbook;

    public businessstatistic(Context pcontext) {
        super(pcontext);
        mbusinessuser = new businessuser(pcontext);
        mbusinessbook = new businessbook(pcontext);
        mbusinesspayout = new businesspayout(pcontext);
    }

    public String getPayOutUserIdByBookId(int pbookid) {
        String _result = "";
        List<statistic> _liststatistictotal = getPayOutUserId(" And pBookId = " + pbookid);
        for (int i = 0; i < _liststatistictotal.size(); i++) {
            statistic _statistic = _liststatistictotal.get(i);
            if (_statistic.getmpayouttype().equals("个人")) {
                _result += _statistic.payuserid + "个人消费" + _statistic.cost.toString() + "元\r\n";
            } else if (_statistic.getmpayouttype().equals("均分")) {
                if (_statistic.payuserid.equals(_statistic.consumeruserid)) {
                    _result += _statistic.payuserid + "个人消费" + _statistic.cost.toString() + "元\r\n";
                } else {
                    _result += _statistic.consumeruserid + "应支付给" + _statistic.payuserid + _statistic.cost + "元\r\n";
                }
            }
        }
        return _result;
    }

    public List<statistic> getPayOutUserId(String pcondition) {
        //get statistic list
        List<statistic> _liststatistic = getstatisticList(pcondition);
        //templist
        List<statistic> _liststatistictmp = new ArrayList<statistic>();
        //save total statistic
        List<statistic> _liststatistictotal = new ArrayList<statistic>();
        String _result = "";
        for(int i = 0;i < _liststatistic.size();i++)
        {
            //get a statistic message
            statistic _statistic = _liststatistic.get(i);
            _result += _statistic.payuserid+"#"+_statistic.consumeruserid+"#"+_statistic.cost+"\r\n";
            //save current payuser id
            String _currentpayuserid = _statistic.payuserid;

            //save to temp
            statistic _statistictemp = new statistic();
            _statistictemp.payuserid = _statistic.payuserid;
            _statistictemp.consumeruserid = _statistic.consumeruserid;
            _statistictemp.cost = _statistic.cost;
            _statistictemp.setmpayouttype(_statistic.getmpayouttype());
            _liststatistictmp.add(_statistictemp);
            //count next index
            int _nextindex;
            if((i + 1) < _liststatistic.size())
            {
                _nextindex = i + 1;
            }
           else{
                _nextindex = i;
            }
            if(!_currentpayuserid.equals(_liststatistic.get(_nextindex).payuserid)||_nextindex == i)
            {
                for(int j = 0; j < _liststatistictmp.size();j++)
                {
                        statistic _statistictotal = _liststatistictmp.get(j);
                        int _index = getPositionByConsumerUserId(_liststatistictotal,_statistictotal.payuserid,_statistictotal.consumeruserid);
                        if(_index != -1)
                        {
                            _liststatistictotal.get(_index).cost = _liststatistictotal.get(_index).cost.add(_statistictotal.cost);
                        }else{
                            _liststatistictotal.add(_statistictotal);
                        }
                }
                _liststatistictmp.clear();
            }

        }
        return _liststatistictotal;
    }

    private int getPositionByConsumerUserId(List<statistic> pliststatistic, String ppayuserid, String pconsumeruserid) {
        int _index = -1;
        for (int i = 0; i < pliststatistic.size(); i++) {
            if (pliststatistic.get(i).payuserid.equals(ppayuserid) && pliststatistic.get(i).consumeruserid.equals(pconsumeruserid)) {
                _index = i;
            }
        }
        return _index;
    }

    private List<statistic> getstatisticList(String pcondition) {
        //按userid排序取出payout
        List<PayOut> _listpayout = mbusinesspayout.getpayoutoderbyuserid(pcondition);
        //获得计算方式数组
        String _payouttypearray[] = getcontext().getResources().getStringArray(R.array.payouttype);

        List<statistic> _liststatistic = new ArrayList<statistic>();

        if (_listpayout != null) {
            for (int i = 0; i < _listpayout.size(); i++) {
                //get a payout
                PayOut _payout = _listpayout.get(i);
                //turn id to name
                String _payoutusername[] = mbusinessuser.getusernamebyuserid(_payout.getPayUserId()).split(",");
                String _payoutuserid[] = _payout.getPayUserId().split(",");
                //get count type
                String _payouttype = _payout.getPayMean();
                //_cost is the cost number
                BigDecimal _cost;
                //if paytype is averange
                if (_payouttype.equals(_payouttypearray[0])) {
                    //get payout user number
                    int _payoutusernumber = _payoutusername.length;
                    _cost = _payout.getAmount().divide(new BigDecimal(_payoutusernumber), 2, BigDecimal.ROUND_HALF_EVEN);
                }else
                {
                    _cost = _payout.getAmount();
                }
                for(int j = 0;j < _payoutuserid.length;j++)
                {
                    //if is lend, skip first user
                    if(_payouttype.equals(_payouttypearray[1]) &&j == 0 )
                    {
                        continue;
                    }
                    // get a staitstic
                    statistic _statistic =  new statistic();
                    _statistic.payuserid = _payoutusername[0];
                    _statistic.consumeruserid = _payoutusername[j];
                    _statistic.setmpayouttype(_payouttype);
                    _statistic.cost = _cost;
                    _liststatistic.add(_statistic);
                }
            }
        }
        return _liststatistic;

    }

    // public String exporstatistics(int pbookid) {}
    public class statistic {
        public String payuserid;
        public String consumeruserid;
        private String mpayouttype;
        public BigDecimal cost;

        public String getmpayouttype() {
            return mpayouttype;
        }

        public void setmpayouttype(String ppayouttype) {
            mpayouttype = ppayouttype;
        }
    }
}
