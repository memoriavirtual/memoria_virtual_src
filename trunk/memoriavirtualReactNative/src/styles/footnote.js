import {
  heightPercentageToDP as hp,
  widthPercentageToDP as wp,
} from 'react-native-responsive-screen';

const footnoteStyles = () => ({
  footnoteText: {
    fontSize: 14,
    marginTop: hp('26%'),
    textAlign: 'center',
    // alignSelf: 'flex-end',
  },
});

export default footnoteStyles;