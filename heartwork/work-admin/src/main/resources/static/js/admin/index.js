define(function(require, exports, moudles) {
	var $ = require('jquery');
	/*var myprovince = remote_ip_info['province'];
	var mycity = remote_ip_info['city']
	var mydistrict = remote_ip_info['district'];
	var country = remote_ip_info['country'];*/
	var $ = require('jquery');
	var template = require('arttemplate');
	var dialog = require('artDialog');
	window.dialog = dialog;
	require('js/common/highcharts-cmd.js');
	var $baseRoot = $("#baseRoot");
	
	var baseRoot = $baseRoot.attr("href");
	var url = {
			materialPrice : baseRoot + "/admin/material/price",
			materialImg : baseRoot + "/admin/material/img",
			priceConversion : baseRoot + "/admin/material/price/conversion",
			material : baseRoot + "/admin/material",	
			periodicalInfo : baseRoot + "/admin/periodical/info",	
	};
	
	
	
	$('#char-container1').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '样例demo'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: false
                },
                showInLegend: true
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['Safari',   45.0],
                ['Opera',       26.8],
                { name: 'Chrome',
                    y: 12.8,
                    sliced: true,
                    selected: true
                },
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['Others',   0.7]
            ]
        }]
    });
    $('#char-container4').highcharts({
        chart: {
            type: 'spline'
        },
        title: {
            text: 'Snow depth at Vikjafjellet, Norway'
        },
        subtitle: {
            text: 'Irregular time data in Highcharts JS'
        },
        xAxis: {
            type: 'datetime',
            dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            },
            title: {
                text: 'Date'
            }
        },
        yAxis: {
            title: {
                text: 'Snow depth (m)'
            },
            min: 0
        },
        tooltip: {
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '{point.x:%e. %b}: {point.y:.2f} m'
        },
        plotOptions: {
            spline: {
                marker: {
                    enabled: true
                }
            }
        },
        series: [{
            name: 'Winter 2007-2008',
            // Define the data points. All series have a dummy year
            // of 1970/71 in order to be compared on the same x axis. Note
            // that in JavaScript, months start at 0 for January, 1 for February etc.
            data: [
                [Date.UTC(1970,  9, 27), 0   ],
                [Date.UTC(1970, 10, 10), 0.6 ],
                [Date.UTC(1970, 10, 18), 0.7 ],
                [Date.UTC(1970, 11,  2), 0.8 ],
                [Date.UTC(1970, 11,  9), 0.6 ],
                [Date.UTC(1970, 11, 16), 0.6 ],
                [Date.UTC(1970, 11, 28), 0.67],
                [Date.UTC(1971,  0,  1), 0.81],
                [Date.UTC(1971,  0,  8), 0.78],
                [Date.UTC(1971,  0, 12), 0.98],
                [Date.UTC(1971,  0, 27), 1.84],
                [Date.UTC(1971,  1, 10), 1.80],
                [Date.UTC(1971,  1, 18), 1.80],
                [Date.UTC(1971,  1, 24), 1.92],
                [Date.UTC(1971,  2,  4), 2.49],
                [Date.UTC(1971,  2, 11), 2.79],
                [Date.UTC(1971,  2, 15), 2.73],
                [Date.UTC(1971,  2, 25), 2.61],
                [Date.UTC(1971,  3,  2), 2.76],
                [Date.UTC(1971,  3,  6), 2.82],
                [Date.UTC(1971,  3, 13), 2.8 ],
                [Date.UTC(1971,  4,  3), 2.1 ],
                [Date.UTC(1971,  4, 26), 1.1 ],
                [Date.UTC(1971,  5,  9), 0.25],
                [Date.UTC(1971,  5, 12), 0   ]
            ]
        }, {
            name: 'Winter 2008-2009',
            data: [
                [Date.UTC(1970,  9, 18), 0   ],
                [Date.UTC(1970,  9, 26), 0.2 ],
                [Date.UTC(1970, 11,  1), 0.47],
                [Date.UTC(1970, 11, 11), 0.55],
                [Date.UTC(1970, 11, 25), 1.38],
                [Date.UTC(1971,  0,  8), 1.38],
                [Date.UTC(1971,  0, 15), 1.38],
                [Date.UTC(1971,  1,  1), 1.38],
                [Date.UTC(1971,  1,  8), 1.48],
                [Date.UTC(1971,  1, 21), 1.5 ],
                [Date.UTC(1971,  2, 12), 1.89],
                [Date.UTC(1971,  2, 25), 2.0 ],
                [Date.UTC(1971,  3,  4), 1.94],
                [Date.UTC(1971,  3,  9), 1.91],
                [Date.UTC(1971,  3, 13), 1.75],
                [Date.UTC(1971,  3, 19), 1.6 ],
                [Date.UTC(1971,  4, 25), 0.6 ],
                [Date.UTC(1971,  4, 31), 0.35],
                [Date.UTC(1971,  5,  7), 0   ]
            ]
        }, {
            name: 'Winter 2009-2010',
            data: [
                [Date.UTC(1970,  9,  9), 0   ],
                [Date.UTC(1970,  9, 14), 0.15],
                [Date.UTC(1970, 10, 28), 0.35],
                [Date.UTC(1970, 11, 12), 0.46],
                [Date.UTC(1971,  0,  1), 0.59],
                [Date.UTC(1971,  0, 24), 0.58],
                [Date.UTC(1971,  1,  1), 0.62],
                [Date.UTC(1971,  1,  7), 0.65],
                [Date.UTC(1971,  1, 23), 0.77],
                [Date.UTC(1971,  2,  8), 0.77],
                [Date.UTC(1971,  2, 14), 0.79],
                [Date.UTC(1971,  2, 24), 0.86],
                [Date.UTC(1971,  3,  4), 0.8 ],
                [Date.UTC(1971,  3, 18), 0.94],
                [Date.UTC(1971,  3, 24), 0.9 ],
                [Date.UTC(1971,  4, 16), 0.39],
                [Date.UTC(1971,  4, 21), 0   ]
            ]
        }]
    });
	
	 $('#char-container2').highcharts({
	        chart: {
	            plotBackgroundColor: null,
	            plotBorderWidth: 0,
	            plotShadow: false
	        },
	        title: {
	            text: 'Browser<br>shares',
	            align: 'center',
	            verticalAlign: 'middle',
	            y: 50
	        },
	        tooltip: {
	            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                dataLabels: {
	                    enabled: true,
	                    distance: -50,
	                    style: {
	                        fontWeight: 'bold',
	                        color: 'white',
	                        textShadow: '0px 1px 2px black'
	                    }
	                },
	                startAngle: -90,
	                endAngle: 90,
	                center: ['50%', '75%']
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: 'Browser share',
	            innerSize: '50%',
	            data: [
	                ['Firefox',   45.0],
	                ['IE',       26.8],
	                ['Chrome', 12.8],
	                ['Safari',    8.5],
	                ['Opera',     6.2],
	                {
	                    name: 'Others',
	                    y: 0.7,
	                    dataLabels: {
	                        enabled: false
	                    }
	                }
	            ]
	        }]
	    });
});