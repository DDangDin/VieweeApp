package com.capstone.vieweeapp

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.capstone.vieweeapp.navigation.nav.VieweeBottomNavigation
import com.capstone.vieweeapp.ui.theme.VieweeAppTheme
import com.capstone.vieweeapp.utils.Constants
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import org.opencv.android.OpenCVLoader
import java.io.File

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val TAG = "MainActivity(LOG)"

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val interviewActivityIntent = Intent(this, InterviewActivity::class.java)

        val str =
            "#1피드백\n주어진 답변에서는 사용자가 졸업 작품과 인턴 경험을 통해 배운 주요 역량인 시장 조사와 타게팅 능력을 언급하였습니다. 이는 질문에 대한 적절한 답변이었으며, 이 역량을 이번 포지션에서 어떻게 활용할 것인지에 대한 구체적인 말은 없었습니다. 사용자에게 질문하여 구체적인 계획이나 아이디어를 물어볼 수 있을 것입니다.\n\n#2피드백\n사용자는 시계와 수저통 제품 출시 과정에서의 도전적인 순간에 대해 언급하였습니다. 이는 질문에 대한 적절한 답변이었으며, 어떻게 극복했는지에 대해 구체적인 내용은 제공되지 않았습니다. 채민지님에게 이 도전을 극복하는데 어떤 노력이나 전략을 사용했는지 추가로 질문하여 답변을 받을 필요가 있을 것입니다.\n#3피드백\n사용자는 제품 디자인 및 개발 프로세스에서 담당한 역할과 프로젝트 팀과의 협업 경험에 대해 언급하지 않았습니다. 따라서, 추가적인 정보를 얻기 위해 사용자에게 질문하여 이에 대한 답변을 받을 필요가 있습니다.\n#4피드백\n사용자의 자기개발에 대한 언급이 없습니다. 디자인 분야에서 계속 발전하기 위해 어떤 노력을 하고 있는지에 대해 물어보면 좋을 것 같습니다.\n#5피드백\n사용자는 디자인 분야에서 결과물에 대한 긍정적인 반응이 가장 열정적인 요소라고 언급하였습니다. 이는 질문에 대한 적절한 답변이었으며, 추가적인 피드백이 필요하지 않습니다.\n#총평 피드백\n사용자는 주어진 질문에 대한 대답을 하였지만, 몇 가지 질문에 대한 구체적인 내용이 부족하거나 누락되어 있습니다. 또한, 자기개발에 대해 언급하지 않은 점이 아쉽습니다. 사용자의 역량과 경험은 어느 정도 보여주지만, 더 구체적인 답변을 통해 사용자의 역량과 차별점을 더 잘 보여줄 수 있을 것입니다. 최종적으로는 면접에서 더 자세한 내용을 물어보아야 사용자의 역량과 적합도를 파악할 수 있을 것으로 판단됩니다."
                .split(Constants.FEEDBACK_SEPARATOR)
                .filter { it.isNotEmpty() }
                .filter { it.length >= 11 }
                .map {
                    if (it.contains(":")) {
                        it.split(":")[1]
                    } else {
                        it
                    }
                }

        setContent {

            LaunchedEffect(key1 = Unit) {
                str.forEach { item ->
                    Log.d("String_Test", item)
                }
            }

            VieweeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Text(text = "Hello!")

//                    val navController = rememberNavController()
                    val navController = rememberAnimatedNavController()
                    VieweeBottomNavigation(
                        navController = navController,
                        startSelectResume = {
                            interviewActivityIntent.putExtra(
                                Constants.PUT_EXTRA_IS_RE_INTERVIEW,
                                false
                            )
                            startActivity(interviewActivityIntent)
                        },
                        onStartReInterview = { id ->
                            interviewActivityIntent.putExtra(
                                Constants.PUT_EXTRA_IS_RE_INTERVIEW,
                                true
                            )
                            interviewActivityIntent.putExtra(
                                Constants.PUT_EXTRA_PREVIOUS_INTERVIEW_RESULT_INDEX,
                                id
                            )
                            startActivity(interviewActivityIntent)
                        }
                    )
                }
            }
        }
    }
}