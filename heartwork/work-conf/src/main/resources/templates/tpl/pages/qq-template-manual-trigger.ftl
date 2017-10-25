<script type="text/template" id="qq-template-manual-trigger">
        <div class="qq-uploader-selector qq-uploader" qq-drop-area-text="拖拽文件到这里试试">
            <div class="qq-total-progress-bar-container-selector qq-total-progress-bar-container">
                <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-total-progress-bar-selector qq-progress-bar qq-total-progress-bar"></div>
            </div>
            <div class="qq-upload-drop-area-selector qq-upload-drop-area" qq-hide-dropzone>
                <span class="qq-upload-drop-area-text-selector"></span>
            </div>
            <div class="buttons">
                <div class="qq-upload-button-selector qq-upload-button" >
                    <span> <i class="fa fa-file"></i> 选择文件</span>
                </div>
                <button type="button" id="trigger-upload" class="btn btn-primary">
                    <i class="fa fa-cloud-upload"></i> 开始上传
                </button>
            </div>
            <span class="qq-drop-processing-selector qq-drop-processing">
                <span>Processing dropped files...</span>
                <span class="qq-drop-processing-spinner-selector qq-drop-processing-spinner"></span>
            </span>
            <ul class="qq-upload-list-selector qq-upload-list" aria-live="polite" aria-relevant="additions removals">
                <li>

                    <span class="qq-upload-spinner-selector qq-upload-spinner"></span>
                    <img class="qq-thumbnail-selector" qq-max-size="100" qq-server-scale>
                    <span class="qq-upload-file-selector qq-upload-file"></span>
                    <span class="qq-edit-filename-icon-selector qq-edit-filename-icon" aria-label="修改文件名">
                    	<i class="fa fa-edit"></i> 
                    </span>
                    <input class="qq-edit-filename-selector qq-edit-filename" tabindex="0" type="text">
                    <span class="qq-upload-size-selector qq-upload-size"></span>
                    <span  class="qq-upload-cancel-selector"><i class="fa fa-unlink"></i></span>
                    <span type="button" class="qq-upload-retry-selector"><i class="fa fa-repeat"></i></span>
                    <span type="button" class="qq-upload-delete-selector"><i class="fa fa-trash-o fa-fw"></i></span>
                    <span role="status" class="qq-upload-status-text-selector qq-upload-status-text"></span>
                   <div class="qq-progress-bar-container-selector">		
                        <div role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" class="qq-progress-bar-selector qq-progress-bar"></div>
                    </div>
                </li>
            </ul>
        </div>
    </script>