<?php

require_once 'dompdf/autoload.inc.php';

use Dompdf\Dompdf;

$dompdf = new Dompdf();
$html = file_get_contents("lap.html");
$dompdf->loadHtml($html);
$dompdf->setPaper('A5', 'landscape');
$dompdf->render();

$dompdf->stream('codexworld',array('Attachment'=>0));

?>