<?php

require_once 'dompdf/autoload.inc.php';

use Dompdf\Dompdf;

$dompdf = new Dompdf();
$html = file_get_contents("lap2.html");
$dompdf->loadHtml($html);
//$customPaper = array(0,0,595,841);
$dompdf->setPaper('A7','portrait');
$dompdf->render();

$dompdf->stream('codexworld',array('Attachment'=>0));

?>