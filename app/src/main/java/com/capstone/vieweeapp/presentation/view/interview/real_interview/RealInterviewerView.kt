package com.capstone.vieweeapp.presentation.view.interview.real_interview

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

@Preview
@Composable
fun RealInterviewerViewPreview() {
    RealInterviewerView()
}

@Composable
fun RealInterviewerView(modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun ViewerPlayerView(modifier: Modifier = Modifier) {
    val mContext = LocalContext.current
    val videoFilePath = "/storage/emulated/0/Download/talking.mp4" // 동영상 파일 경로
    val videoUri = Uri.parse("file://$videoFilePath") // 파일 경로를 Uri로 변환
    val mediaItem = MediaItem.fromUri(videoUri)

    // 재생관련 설정
    val mExoPlayer = remember(mContext) {
        ExoPlayer.Builder(mContext).build().apply {
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
            volume = 0f
            repeatMode = Player.REPEAT_MODE_ALL
        }
    }

    Box(
        modifier=Modifier.padding(bottom=400.dp)) {
        AndroidView(factory={context->
            StyledPlayerView(context).apply {
                player=mExoPlayer
                useController=false
                resizeMode= AspectRatioFrameLayout.RESIZE_MODE_ZOOM //화면을 완전히 채움,가로세로비율 유지x
            }
        })
    }
}
