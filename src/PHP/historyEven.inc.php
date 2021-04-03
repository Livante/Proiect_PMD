<?php

if (isset($_POST['search'])) {
    $roomId = $_POST['roomId'];
    $function = $_POST['function'];
    $badgeCode = $_POST['badgeCode'];
    $accessDate = $_POST['accessDate'];
    $verdict = $_POST['verdict'];
    $startDate = substr($accessDate, 0, 5) . substr($accessDate, 5, 3) . substr($accessDate, 8, 3) . substr($accessDate, 11, 9);
    $endDate = substr($accessDate, 22, 5) . substr($accessDate, 27, 3) . substr($accessDate, 30, 3) . substr($accessDate, 33, 9);
    // search in all table columns
    // using concat mysql function
    $check=intval(substr($roomId,strlen($roomId)-1,1));
    $query = "SELECT * FROM history WHERE `roomId` LIKE '%" . $roomId . "%' AND MOD($check,2)=0 AND `function` LIKE '%" . $function . "%' 
    AND `badgeCode` LIKE '%" . $badgeCode . "%' 
    AND (`accessDate` BETWEEN '" . $startDate . "' AND '" . $endDate . "')
    AND `verdict` LIKE '%" . $verdict . "%';";
    $search_result = filterTable($query);

} else {
    $query = "SELECT * FROM history WHERE MOD(SUBSTRING(`roomId`,LENGTH(`roomId`),1),2)=0;";
    $search_result = filterTable($query);
}

if (isset($_POST['reset'])) {
    $query = "SELECT * FROM history WHERE MOD(SUBSTRING(`roomId`,LENGTH(`roomId`),1),2)=0;";
    $search_result = filterTable($query);
}
// function to connect and execute the query
function filterTable($query)
{
    $connect = mysqli_connect("localhost", "root", "", "history");
    $filter_Result = mysqli_query($connect, $query);
    return $filter_Result;
}

?>

<!DOCTYPE html>
<html lang="en">
<head>
    <title></title>
    <style>
        table, tr, th, td {
            border: 1px solid black;
        }
    </style>

    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/Proiect_PMD/src/css/bootstrap.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="/Proiect_PMD/src/css/theme-hyperblue.css" rel="stylesheet" type="text/css" media="all"/>
    <link href="/Proiect_PMD/src/css/custom.css" rel="stylesheet" type="text/css" media="all"/>
    <link href='http://fonts.googleapis.com/css?family=Lato:300,400%7CRaleway:100,400,300,500,600,700%7COpen+Sans:400,500,600'
          rel='stylesheet' type='text/css'>
</head>
<body>
<div class="nav-container">
</div>

<div class="main-container">
    <section class="bg-primary">
        <div class="col-sm-6 col-sm-offset-3">
            <form action="historyEven.inc.php" method="post">
                <input type="text" name="roomId"
                       minlength="2" maxlength="4" size="10" target="_blank"
                       class="mb0 transparent validate-require" placeholder="ROOM CODE...">
                <input type="text" name="function" class="mb0 transparent validate-require"
                       placeholder="EMPLOYEE JOB...">
                <input type="text" name="badgeCode"
                       minlength="4" maxlength="4" size="10" class="mb0 transparent validate-require"
                       placeholder="EMPLOYEE CODE...">
                <input type="text" name="accessDate" class="mb0 transparent validate-require" autocomplete="off"
                       placeholder="ACCESS DATE...">
                <select name="verdict" id="verdict" class="mb0 transparent validate-require">
                    <option value=" "></option>
                    <option value="Access Granted">Access Granted</option>
                    <option value="Access Denied">Access Denied</option>
                </select>
                <input type="submit" name="search" value="Filter EVEN"><br><br>

                <input type="submit" name="reset" value="Reset filters"><br><br>

                <table class="col-sm-10 col-sm-offset-1">
                    <tr>
                        <th style="text-align: center; vertical-align: middle;">ROOM CODE</th>
                        <th style="text-align: center; vertical-align: middle;">EMPLOYEE JOB</th>
                        <th style="text-align: center; vertical-align: middle;">EMPLOYEE CODE</th>
                        <th style="text-align: center; vertical-align: middle;">ACCESS DATE</th>
                        <th style="text-align: center; vertical-align: middle;">VERDICT</th>
                    </tr>

                    <!-- populate table from mysql database -->
                    <?php while ($row = mysqli_fetch_array($search_result)): ?>
                        <tr>
                            <td style="text-align: center; vertical-align: middle;"><?php echo $row['roomId']; ?></td>
                            <td style="text-align: center; vertical-align: middle;"><?php echo $row['function']; ?></td>
                            <td style="text-align: center; vertical-align: middle;"><?php echo $row['badgeCode']; ?></td>
                            <td style="text-align: center; vertical-align: middle;"><?php echo $row['accessDate']; ?></td>
                            <td style="text-align: center; vertical-align: middle;"><?php echo $row['verdict']; ?></td>
                        </tr>
                    <?php endwhile; ?>
                </table>
            </form>
        </div>
</div>

<div class="row">
    <div class="col-sm-12 text-center">
        <p class="fade-half lead mb0"></p>
    </div>
</div>

</div>

</section>
<section>
    <div class="container">
        <div class="row">
            <div class="col-md-8 col-md-offset-2 col-sm-12 text-center">

                <div class="tabbed-content button-tabs">
                    <ul class="tabs">
                        <li class="active">
                            <div class="tab-title">
		                                    <span>
												<a href="http://localhost:63342/Proiect_PMD/WEB/Main%20menu.html?_ijt=5a3fhbq0jfp7kdh1e7hlhko4rn">
												<font color="black">
													Cancel
												</font>
											</a>
											</span>
                            </div>

                        </li>

                    </ul>
                </div>

            </div>
        </div>

    </div>


</section>
</div>


<script src="/Proiect_PMD/src/js/jquery.min.js"></script>
<script src="/Proiect_PMD/src/js/bootstrap.min.js"></script>
<script src="/Proiect_PMD/src/js/parallax.js"></script>
<script src="/Proiect_PMD/src/js/scripts.js"></script>

<script type="text/javascript" src="https://cdn.jsdelivr.net/jquery/latest/jquery.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/momentjs/latest/moment.min.js"></script>
<script type="text/javascript" src="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css"/>

<script>
    // $(function() {
    //     $('input[name="accessDate"]').daterangepicker({
    //         timePicker24Hour:true,
    //         timePicker: true,
    //         startDate: moment().startOf('hour'),
    //         endDate: moment().startOf('hour').add(32, 'hour'),
    //         locale: {
    //             format: 'YYYY-MM-DD HH:mm:ss'
    //         }
    //     });
    // });
    $(function () {

        $('input[name="accessDate"]').daterangepicker({
            timePicker24Hour: true,
            autoUpdateInput: false,
            timePicker: true,
            locale: {
                cancelLabel: 'Clear'
            }
        });

        $('input[name="accessDate"]').on('apply.daterangepicker', function (ev, picker) {
            $(this).val(picker.startDate.format('YYYY-MM-DD HH:mm:ss') + ' - ' + picker.endDate.format('YYYY-MM-DD HH:mm:ss'));
        });

        $('input[name="accessDate"]').on('cancel.daterangepicker', function (ev, picker) {
            $(this).val('');
        });

    });
</script>

</body>
</html>